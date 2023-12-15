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
public class FeatureProjectDependency extends DelegatingProjectDependency {

    @Inject
    public FeatureProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":feature:about"
     */
    public Feature_AboutProjectDependency getAbout() { return new Feature_AboutProjectDependency(getFactory(), create(":feature:about")); }

    /**
     * Creates a project dependency on the project at path ":feature:favorites"
     */
    public Feature_FavoritesProjectDependency getFavorites() { return new Feature_FavoritesProjectDependency(getFactory(), create(":feature:favorites")); }

    /**
     * Creates a project dependency on the project at path ":feature:movie"
     */
    public Feature_MovieProjectDependency getMovie() { return new Feature_MovieProjectDependency(getFactory(), create(":feature:movie")); }

    /**
     * Creates a project dependency on the project at path ":feature:recommendations"
     */
    public Feature_RecommendationsProjectDependency getRecommendations() { return new Feature_RecommendationsProjectDependency(getFactory(), create(":feature:recommendations")); }

    /**
     * Creates a project dependency on the project at path ":feature:search"
     */
    public Feature_SearchProjectDependency getSearch() { return new Feature_SearchProjectDependency(getFactory(), create(":feature:search")); }

}
