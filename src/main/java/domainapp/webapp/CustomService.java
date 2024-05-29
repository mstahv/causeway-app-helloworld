package domainapp.webapp;

import domainapp.modules.hello.dom.hwo.HelloWorldObject;
import domainapp.modules.hello.dom.hwo.HelloWorldObjects;

import org.apache.causeway.applib.services.iactnlayer.InteractionContext;
import org.apache.causeway.applib.services.iactnlayer.InteractionService;
import org.apache.causeway.applib.services.user.UserMemento;
import org.apache.causeway.applib.services.xactn.TransactionalProcessor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * A service for Vaadin UI that opens selected API. Impersonates backend calls as "sven".
 */
@Service
public class CustomService {

    private final InteractionService interactionService;
    private final TransactionalProcessor transactionalProcessor;
    private final HelloWorldObjects helloWorldObjects;

    public CustomService(InteractionService interactionService, TransactionalProcessor transactionalProcessor, HelloWorldObjects helloWorldObjects) {
        this.interactionService = interactionService;
        this.transactionalProcessor = transactionalProcessor;
        this.helloWorldObjects = helloWorldObjects;
    }

    public List<HelloWorldObject> findAllHellos() {
        return callAsSven(() -> helloWorldObjects.listAll()).get();
    }


    private <T> Optional<T> callAsSven( final Callable<T> callable) {
        return interactionService.call(
                        InteractionContext.ofUserWithSystemDefaults(UserMemento.ofName("sven")),
                        () -> transactionalProcessor.callWithinCurrentTransactionElseCreateNew(callable)
                )
                .ifFailureFail()
                .getValue();
    }

}
