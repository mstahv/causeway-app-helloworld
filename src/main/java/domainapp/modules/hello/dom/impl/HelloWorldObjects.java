package domainapp.modules.hello.dom.impl;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.jdo.jdosupport.IsisJdoSupport_v3_2;

import domainapp.modules.hello.dom.types.Name;

@DomainService(
        nature = NatureOfService.VIEW,
        objectType = "helloworld.HelloWorldObjects"
        )
public class HelloWorldObjects {

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public HelloWorldObject create(
            @Name final String name) {
        return repositoryService.persist(new HelloWorldObject(name));
    }
    public String default0Create() {
        return "Hello World!";
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public List<HelloWorldObject> findByName(
            @Name final String name) {
        JDOQLTypedQuery<HelloWorldObject> q = isisJdoSupport.newTypesafeQuery(HelloWorldObject.class);
        final QHelloWorldObject cand = QHelloWorldObject.candidate();
        q = q.filter(
                cand.name.indexOf(q.stringParameter("name")).ne(-1)
                );
        return q.setParameter("name", name)
                .executeList();
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    public List<HelloWorldObject> listAll() {
        return repositoryService.allInstances(HelloWorldObject.class);
    }

    @Inject RepositoryService repositoryService;
    @Inject IsisJdoSupport_v3_2 isisJdoSupport;

}
