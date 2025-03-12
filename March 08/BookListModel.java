package com.mytraining.core.models;

import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.Result;
import com.day.cq.search.predicates.PathPredicate;
import com.day.cq.search.predicates.PropertyPredicate;
import com.day.cq.search.predicates.TypePredicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BookListModel {

    @ValueMapValue
    private String bookRootPagePath;

    @ValueMapValue
    private String loadMoreCta;

    @SlingObject
    private Resource resource;

    @OSGiService
    private QueryBuilder queryBuilder;

    private List<Book> books = new ArrayList<>();

    @PostConstruct
    protected void init() {
        if (StringUtils.isNotBlank(bookRootPagePath)) {
            fetchBooks();
        }
    }

    private void fetchBooks() {
        try {
            Query query = queryBuilder.createQuery();
            query.addPredicate(new PathPredicate(bookRootPagePath));
            query.addPredicate(new TypePredicate("cq:Page"));
            query.addPredicate(new PropertyPredicate("@jcr:content/cq:lastModified", PropertyPredicate.Operator.EXISTS));
            query.addSortBy("@jcr:content/cq:lastModified", Query.SortOrder.DESC);

            Result result = query.getResult();
            for (Resource bookPage : result.getResources()) {
                books.add(new Book(bookPage));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public String getLoadMoreCta() {
        return loadMoreCta;
    }

    public static class Book {
        private final String title;
        private final String image;
        private final String description;
        private final String publishDate;

        public Book(Resource resource) {
            this.title = resource.getValueMap().get("jcr:title", String.class);
            this.image = resource.getValueMap().get("image", String.class);
            this.description = resource.getValueMap().get("description", String.class);
            this.publishDate = resource.getValueMap().get("publishDate", String.class);
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public String getDescription() {
            return description;
        }

        public String getPublishDate() {
            return publishDate;
        }
    }
}
