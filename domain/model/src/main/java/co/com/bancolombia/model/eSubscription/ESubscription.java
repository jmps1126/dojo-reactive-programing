package co.com.bancolombia.model.eSubscription;

import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterpriseValitation.EnterpriseValitation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ESubscription{

    private EnterpriseValitation validation;
    private Enterprise enterprise;
    private Agreement agreement;

    public static ESubscription createSubscription(EnterpriseValitation ev){
        Agreement agreement = Agreement.builder().build();
        return ESubscription.builder()
                .validation(ev)
                .enterprise(ev.getEnterprise())
                .agreement(agreement)
                .build();
    }

}
