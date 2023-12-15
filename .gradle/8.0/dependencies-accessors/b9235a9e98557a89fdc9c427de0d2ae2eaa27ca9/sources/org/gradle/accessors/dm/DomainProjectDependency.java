package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class DomainProjectDependency extends DelegatingProjectDependency {

    @Inject
    public DomainProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":domain:models"
     */
    public Domain_ModelsProjectDependency getModels() { return new Domain_ModelsProjectDependency(getFactory(), create(":domain:models")); }

    /**
     * Creates a project dependency on the project at path ":domain:navigator"
     */
    public Domain_NavigatorProjectDependency getNavigator() { return new Domain_NavigatorProjectDependency(getFactory(), create(":domain:navigator")); }

}
