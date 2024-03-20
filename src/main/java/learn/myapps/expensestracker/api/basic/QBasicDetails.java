package learn.myapps.expensestracker.api.basic;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QBasicDetails is a Querydsl query type for BasicDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBasicDetails extends EntityPathBase<BasicDetails> {

    private static final long serialVersionUID = 548285791L;

    public static final QBasicDetails basicDetails = new QBasicDetails("basicDetails");

    public final NumberPath<Long> basicId = createNumber("basicId", Long.class);

    public final StringPath description = createString("description");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    public final StringPath lastUpdatedBy = createString("lastUpdatedBy");

    public final DateTimePath<java.time.LocalDateTime> lastUpdatedDateAndTime = createDateTime("lastUpdatedDateAndTime", java.time.LocalDateTime.class);

    public QBasicDetails(String variable) {
        super(BasicDetails.class, forVariable(variable));
    }

    public QBasicDetails(Path<? extends BasicDetails> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBasicDetails(PathMetadata metadata) {
        super(BasicDetails.class, metadata);
    }

}

