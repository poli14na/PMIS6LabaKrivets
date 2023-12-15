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
public class MorozovaProjectDependency extends DelegatingProjectDependency {

    @Inject
    public MorozovaProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":app"
     */
    public AppProjectDependency getApp() { return new AppProjectDependency(getFactory(), create(":app")); }

    /**
     * Creates a project dependency on the project at path ":arch"
     */
    public ArchProjectDependency getArch() { return new ArchProjectDependency(getFactory(), create(":arch")); }

    /**
     * Creates a project dependency on the project at path ":data"
     */
    public DataProjectDependency getData() { return new DataProjectDependency(getFactory(), create(":data")); }

    /**
     * Creates a project dependency on the project at path ":domain"
     */
    public DomainProjectDependency getDomain() { return new DomainProjectDependency(getFactory(), create(":domain")); }

    /**
     * Creates a project dependency on the project at path ":feature"
     */
    public FeatureProjectDependency getFeature() { return new FeatureProjectDependency(getFactory(), create(":feature")); }

    /**
     * Creates a project dependency on the project at path ":impl"
     */
    public ImplProjectDependency getImpl() { return new ImplProjectDependency(getFactory(), create(":impl")); }

}
