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
public class ImplProjectDependency extends DelegatingProjectDependency {

    @Inject
    public ImplProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":impl:arch"
     */
    public Impl_ArchProjectDependency getArch() { return new Impl_ArchProjectDependency(getFactory(), create(":impl:arch")); }

    /**
     * Creates a project dependency on the project at path ":impl:database"
     */
    public Impl_DatabaseProjectDependency getDatabase() { return new Impl_DatabaseProjectDependency(getFactory(), create(":impl:database")); }

    /**
     * Creates a project dependency on the project at path ":impl:navigation"
     */
    public Impl_NavigationProjectDependency getNavigation() { return new Impl_NavigationProjectDependency(getFactory(), create(":impl:navigation")); }

    /**
     * Creates a project dependency on the project at path ":impl:network"
     */
    public Impl_NetworkProjectDependency getNetwork() { return new Impl_NetworkProjectDependency(getFactory(), create(":impl:network")); }

}
