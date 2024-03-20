package learn.myapps.expensestracker.api.category;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QExpensesCategoryDetails is a Querydsl query type for ExpensesCategoryDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExpensesCategoryDetails extends EntityPathBase<ExpensesCategoryDetails> {

    private static final long serialVersionUID = -2136221756L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExpensesCategoryDetails expensesCategoryDetails = new QExpensesCategoryDetails("expensesCategoryDetails");

    public final ListPath<String, StringPath> alias = this.<String, StringPath>createList("alias", String.class, StringPath.class, PathInits.DIRECT2);

    public final learn.myapps.expensestracker.api.basic.QBasicDetails basicDetails;

    public final StringPath expensesCategory = createString("expensesCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QExpensesCategoryDetails(String variable) {
        this(ExpensesCategoryDetails.class, forVariable(variable), INITS);
    }

    public QExpensesCategoryDetails(Path<? extends ExpensesCategoryDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExpensesCategoryDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExpensesCategoryDetails(PathMetadata metadata, PathInits inits) {
        this(ExpensesCategoryDetails.class, metadata, inits);
    }

    public QExpensesCategoryDetails(Class<? extends ExpensesCategoryDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.basicDetails = inits.isInitialized("basicDetails") ? new learn.myapps.expensestracker.api.basic.QBasicDetails(forProperty("basicDetails")) : null;
    }

}

