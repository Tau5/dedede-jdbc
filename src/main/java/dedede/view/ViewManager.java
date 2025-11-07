package dedede.view;

public class ViewManager {
    View currentView;
    Model model;

    public ViewManager(Model model) {
        this.model = model;
    }

    public void switchView(View view) {
        currentView = view;
        view.run(model, this);
    }
}
