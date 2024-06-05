import SwiftUI
import RoomerFind
import MapLibre

struct ComposeView: UIViewControllerRepresentable {
    private let component: RootComponent
    private let backDispatcher: BackDispatcher
    
    init(_ component: RootComponent, _ backDispatcher: BackDispatcher) {
        self.component = component
        self.backDispatcher = backDispatcher
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            rootComponent: component,
            backDispatcher: backDispatcher,
            mapProvider: IOSMapProvider()
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    private let component: RootComponent
    private let backDispatcher: BackDispatcher
    
    init(_ component: RootComponent, _ backDispatcher: BackDispatcher) {
        self.component = component
        self.backDispatcher = backDispatcher
    }
    
    var body: some View {
        ComposeView(component, backDispatcher)
            .ignoresSafeArea(.all)
    }
}

class IOSMapProvider: ComposeNativeMapProvider {

    func createMap(component: MapComponent) -> ComposeNativeMapView {
        let mapView = MLNMapView(frame: .zero, styleURL: URL(string: component.styleUrl))
        mapView.logoView.isHidden = true
        mapView.attributionButton.isHidden = true
        let mapHelper = MapHelper(component: component)
        let mapController = IOSMapController(mapView, mapHelper)
        mapView.delegate = mapController
        return ComposeNativeMapView(view: mapView, controller: mapController)
    }
}

class IOSMapController: NSObject, MLNMapViewDelegate, ComposeMapController {
    private let mapView: MLNMapView
    private let helper: MapHelper
    private var currentCoordinates: CLLocationCoordinate2D = CLLocationCoordinate2D()
    private var currentZoom: Double = 0.0
    private var currentRadius: Double = 0.0
    private var animating: Bool = false
    
    init(_ mapView: MLNMapView, _ helper: MapHelper) {
        self.mapView = mapView
        self.helper = helper
        super.init()
    }
    
    func setCameraLocation(center: LatLng, zoom: Double) {
        if ((currentCoordinates.latitude != center.lat ||
            currentCoordinates.longitude != center.long_ ||
            currentZoom != zoom) && !animating
        ) {
            mapView.setCenter(
                CLLocationCoordinate2D(
                    latitude: center.lat,
                    longitude: center.long_
                ),
                zoomLevel: zoom,
                animated: true
            )
        }
    }
    
    func mapView(
        _ mapView: MLNMapView,
        regionIsChangingWith: MLNCameraChangeReason
    ) {
        if (regionIsChangingWith == .gestureOneFingerZoom ||
            regionIsChangingWith == .gesturePan ||
            regionIsChangingWith == .gesturePinch ||
            regionIsChangingWith == .gestureZoomIn ||
            regionIsChangingWith == .gestureZoomOut ||
            regionIsChangingWith == .gestureRotate
        ) {
            animating = true
            var shouldUpdate = false
            let metersPerPoint = MLNMapProjection(mapView: mapView).metersPerPoint
            let radius = mapView.frame.size.height * metersPerPoint * 0.8 / 2

            let newCoordinates = CLLocationCoordinate2D(
                latitude: mapView.camera.centerCoordinate.latitude,
                longitude: mapView.camera.centerCoordinate.longitude
            )
            
            let newZoom = mapView.zoomLevel
            
            if (newCoordinates.latitude != currentCoordinates.latitude ||
                newCoordinates.longitude != currentCoordinates.longitude
            ) {
                currentCoordinates = newCoordinates
                shouldUpdate = true
            }
            
            if (newZoom != currentZoom) {
                currentZoom = newZoom
                shouldUpdate = true
            }
            
            if (radius != currentRadius) {
                currentRadius = radius
                shouldUpdate = true
            }
            
            if (shouldUpdate) {
                helper.onMapMoveManual(
                    latitude: currentCoordinates.latitude,
                    longitude: currentCoordinates.longitude,
                    radius: currentRadius / 1000,
                    zoom: currentZoom
                )
            }
        }
    }
    
    func mapView(_ mapView: MLNMapView, regionDidChangeWith reason: MLNCameraChangeReason, animated: Bool) {
        animating = false
    }
}
