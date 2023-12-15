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
public class DataProjectDependency extends DelegatingProjectDependency {

    @Inject
    public DataProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":data:favorites"
     */
    public Data_FavoritesProjectDependency getFavorites() { return new Data_FavoritesProjectDependency(getFactory(), create(":data:favorites")); }

    /**
     * Creates a project dependency on the project at path ":data:movie"
     */
    public Data_MovieProjectDependency getMovie() { return new Data_MovieProjectDependency(getFactory(), create(":data:movie")); }

    /**
     * Creates a project dependency on the project at path ":data:recommendations"
     */
    public Data_RecommendationsProjectDependency getRecommendations() { return new Data_RecommendationsProjectDependency(getFactory(), create(":data:recommendations")); }

    /**
     * Creates a project dependency on the project at path ":data:search"
     */
    public Data_SearchProjectDependency getSearch() { return new Data_SearchProjectDependency(getFactory(), create(":data:search")); }

}
