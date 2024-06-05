import SwiftUI
import RoomerFind

@main
struct iosApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self)
    var appDelegate: AppDelegate
    
    init() {
        KoinHelperKt.doInitKoin()
    }
    
    private var rootHolder: RootHolder { appDelegate.getRootHolder() }
    
    var body: some Scene {
        WindowGroup {
            ContentView(rootHolder.root, rootHolder.backDispatcher)
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
                    LifecycleRegistryExtKt.resume(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
                    LifecycleRegistryExtKt.pause(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)) { _ in
                    LifecycleRegistryExtKt.stop(rootHolder.lifecycle)
                }
                .onReceive(NotificationCenter.default.publisher(for: UIApplication.willTerminateNotification)) { _ in
                    LifecycleRegistryExtKt.destroy(rootHolder.lifecycle)
                }
        }
    }
}

class AppDelegate: NSObject, UIApplicationDelegate {
    private var rootHolder: RootHolder?
    
    fileprivate func getRootHolder() -> RootHolder {
        if (rootHolder == nil) {
            rootHolder = RootHolder()
        }
        return rootHolder!
    }
}

private class RootHolder {
    let lifecycle: LifecycleRegistry
    let instanceKeeper: InstanceKeeperDispatcher
    let root: RootComponent
    let backDispatcher: BackDispatcher
    
    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        instanceKeeper = InstanceKeeperDispatcherKt.InstanceKeeperDispatcher()
        backDispatcher = BackDispatcherKt.BackDispatcher()
        
        root = DefaultRootComponentKt.createRootComponent(
            componentContext: DefaultComponentContext(
                lifecycle: lifecycle,
                stateKeeper: nil,
                instanceKeeper: instanceKeeper,
                backHandler: backDispatcher
            )
        )
        
        LifecycleRegistryExtKt.create(lifecycle)
    }
}
