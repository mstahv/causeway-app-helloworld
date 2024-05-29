package domainapp.webapp;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import domainapp.modules.hello.dom.hwo.HelloWorldObject;
import domainapp.modules.hello.dom.hwo.HelloWorldObjects;

import org.apache.causeway.applib.services.iactnlayer.InteractionService;
import org.apache.causeway.applib.services.xactn.TransactionalProcessor;

@Route
public class CustomView extends VerticalLayout {


    private final CustomService customService;

    public CustomView(CustomService customService) {
        this.customService = customService;
        add(new H1("Hello Causeway!"));

        Grid<HelloWorldObject> grid = new Grid<>(HelloWorldObject.class);
        grid.setItems(customService.findAllHellos());
        add(grid);
    }
}
