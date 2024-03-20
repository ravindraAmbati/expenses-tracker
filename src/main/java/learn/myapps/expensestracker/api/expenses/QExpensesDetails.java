package learn.myapps.expensestracker.api.expenses;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QExpensesDetails is a Querydsl query type for ExpensesDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExpensesDetails extends EntityPathBase<ExpensesDetails> {

    private static final long serialVersionUID = -1872428353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExpensesDetails expensesDetails = new QExpensesDetails("expensesDetails");

    public final NumberPath<java.math.BigDecimal> amount = createNumber("amount", java.math.BigDecimal.class);

    public final learn.myapps.expensestracker.api.basic.QBasicDetails basicDetails;

    public final learn.myapps.expensestracker.api.category.QExpensesCategoryDetails category;

    public final learn.myapps.expensestracker.api.currency.QCurrencyDetails currency;

    public final DateTimePath<java.time.LocalDateTime> dateAndTime = createDateTime("dateAndTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final learn.myapps.expensestracker.api.user.QUserDetails paidBy;

    public final StringPath paidTo = createString("paidTo");

    public final learn.myapps.expensestracker.api.payment.QPaymentModeDetails paymentMode;

    public QExpensesDetails(String variable) {
        this(ExpensesDetails.class, forVariable(variable), INITS);
    }

    public QExpensesDetails(Path<? extends ExpensesDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExpensesDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExpensesDetails(PathMetadata metadata, PathInits inits) {
        this(ExpensesDetails.class, metadata, inits);
    }

    public QExpensesDetails(Class<? extends ExpensesDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.basicDetails = inits.isInitialized("basicDetails") ? new learn.myapps.expensestracker.api.basic.QBasicDetails(forProperty("basicDetails")) : null;
        this.category = inits.isInitialized("category") ? new learn.myapps.expensestracker.api.category.QExpensesCategoryDetails(forProperty("category"), inits.get("category")) : null;
        this.currency = inits.isInitialized("currency") ? new learn.myapps.expensestracker.api.currency.QCurrencyDetails(forProperty("currency"), inits.get("currency")) : null;
        this.paidBy = inits.isInitialized("paidBy") ? new learn.myapps.expensestracker.api.user.QUserDetails(forProperty("paidBy"), inits.get("paidBy")) : null;
        this.paymentMode = inits.isInitialized("paymentMode") ? new learn.myapps.expensestracker.api.payment.QPaymentModeDetails(forProperty("paymentMode"), inits.get("paymentMode")) : null;
    }

}

