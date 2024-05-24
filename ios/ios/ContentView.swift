import SwiftUI
import RoomerFind

struct ComposeView: UIViewControllerRepresentable {
    private let component: RootComponent
    
    init(_ component: RootComponent) {
        self.component = component
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            rootComponent: component
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    private let component: RootComponent
    
    init(_ component: RootComponent) {
        self.component = component
    }
    
    var body: some View {
        ComposeView(component)
            .ignoresSafeArea(.all)
    }
}
