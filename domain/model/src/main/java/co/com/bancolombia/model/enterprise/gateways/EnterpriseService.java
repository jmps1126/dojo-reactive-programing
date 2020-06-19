package co.com.bancolombia.model.enterprise.gateways;

import co.com.bancolombia.model.enterprise.CreditState;
import co.com.bancolombia.model.enterprise.Enterprise;
import co.com.bancolombia.model.enterprise.SuperIntReport;
import co.com.bancolombia.model.enterpriseValitation.EnterpriseValitation;
import reactor.core.publisher.Mono;

public interface EnterpriseService {


    Mono<EnterpriseValitation> validateEnterprise(Enterprise enterprise);

    Mono<Enterprise> searchRestrictions(Enterprise enterprise);

    Mono<SuperIntReport> searchSuperIntReports(Enterprise enterprise);

    Mono<CreditState> serachCreditState(Enterprise enterprise);
}
