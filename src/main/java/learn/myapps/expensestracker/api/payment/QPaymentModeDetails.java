package learn.myapps.expensestracker.api.payment;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QPaymentModeDetails is a Querydsl query type for PaymentModeDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaymentModeDetails extends EntityPathBase<PaymentModeDetails> {

    private static final long serialVersionUID = 1048821900L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaymentModeDetails paymentModeDetails = new QPaymentModeDetails("paymentModeDetails");

    public final StringPath accountDetails = createString("accountDetails");

    public final learn.myapps.expensestracker.api.basic.QBasicDetails basicDetails;

    public final StringPath cardDetails = createString("cardDetails");

    public final StringPath cardType = createString("cardType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath paymentMode = createString("paymentMode");

    public final StringPath upiDetails = createString("upiDetails");

    public QPaymentModeDetails(String variable) {
        this(PaymentModeDetails.class, forVariable(variable), INITS);
    }

    public QPaymentModeDetails(Path<? extends PaymentModeDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaymentModeDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaymentModeDetails(PathMetadata metadata, PathInits inits) {
        this(PaymentModeDetails.class, metadata, inits);
    }

    public QPaymentModeDetails(Class<? extends PaymentModeDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.basicDetails = inits.isInitialized("basicDetails") ? new learn.myapps.expensestracker.api.basic.QBasicDetails(forProperty("basicDetails")) : null;
    }

}

