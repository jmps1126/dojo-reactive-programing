package co.com.bancolombia.model.enterpriseValitation;

import co.com.bancolombia.model.enterprise.CreditState;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprise.SuperIntReport;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class EnterpriseValitation{

    private Enterprise enterprise;
    private boolean nitIsValidate;
    private boolean hasRestriction;
    private CreditState creditState;
    private SuperIntReport superIntReport;


}
