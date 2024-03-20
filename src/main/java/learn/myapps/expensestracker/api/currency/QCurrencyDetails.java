package learn.myapps.expensestracker.api.currency;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QCurrencyDetails is a Querydsl query type for CurrencyDetails
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCurrencyDetails extends EntityPathBase<CurrencyDetails> {

    private static final long serialVersionUID = -915409409L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCurrencyDetails currencyDetails = new QCurrencyDetails("currencyDetails");

    public final learn.myapps.expensestracker.api.basic.QBasicDetails basicDetails;

    public final StringPath currency = createString("currency");

    public final StringPath defaultCurrency = createString("defaultCurrency");

    public final NumberPath<java.math.BigDecimal> exchangeRate = createNumber("exchangeRate", java.math.BigDecimal.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCurrencyDetails(String variable) {
        this(CurrencyDetails.class, forVariable(variable), INITS);
    }

    public QCurrencyDetails(Path<? extends CurrencyDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCurrencyDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCurrencyDetails(PathMetadata metadata, PathInits inits) {
        this(CurrencyDetails.class, metadata, inits);
    }

    public QCurrencyDetails(Class<? extends CurrencyDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.basicDetails = inits.isInitialized("basicDetails") ? new learn.myapps.expensestracker.api.basic.QBasicDetails(forProperty("basicDetails")) : null;
    }

}

