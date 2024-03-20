package learn.myapps.expensestracker.api.user;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUserDetails is a Querydsl query type for UserDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserDetails extends EntityPathBase<UserDetails> {

    private static final long serialVersionUID = 62135871L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserDetails userDetails = new QUserDetails("userDetails");

    public final learn.myapps.expensestracker.api.basic.QBasicDetails basicDetails;

    public final StringPath emailId = createString("emailId");

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath mobileNo = createString("mobileNo");

    public QUserDetails(String variable) {
        this(UserDetails.class, forVariable(variable), INITS);
    }

    public QUserDetails(Path<? extends UserDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserDetails(PathMetadata metadata, PathInits inits) {
        this(UserDetails.class, metadata, inits);
    }

    public QUserDetails(Class<? extends UserDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.basicDetails = inits.isInitialized("basicDetails") ? new learn.myapps.expensestracker.api.basic.QBasicDetails(forProperty("basicDetails")) : null;
    }

}

