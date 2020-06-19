package co.com.bancolombia.usecase.eSubcription;

import co.com.bancolombia.model.eSubscription.ESubscription;
import co.com.bancolombia.model.enterprise.*;
import co.com.bancolombia.model.enterprise.gateways.EnterpriseService;
import co.com.bancolombia.model.enterpriseValitation.EnterpriseValitation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

@RequiredArgsConstructor
public class ESubcriptionUseCase {

    private final EnterpriseService enterpriseService;

    public Mono<ESubscription> subscribeEnterprise(Enterprise enterprise){
        return enterpriseService.validateEnterprise(enterprise)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new EnterpriseNoFoundExeption())))
                .flatMap(this::searchRestrictions)
                .flatMap(this::searchCreditStateAndReports)
                .map(this::addInfoToValidation)
                .map(ESubscription::createSubscription)
                .onErrorMap((e) -> !(e instanceof EnterpriseNoFoundExeption),
                            (e2) -> new EnterpriseException(e2.getMessage()));

    }

    private EnterpriseValitation addInfoToValidation(Tuple3<CreditState, SuperIntReport, EnterpriseValitation> t) {
        return t.getT3().toBuilder()
                .enterprise(t.getT3().getEnterprise())
                .creditState(t.getT1())
                .superIntReport(t.getT2())
                .build();
    }

    private Mono<Tuple3<CreditState,SuperIntReport, EnterpriseValitation>> searchCreditStateAndReports(EnterpriseValitation ev) {

        return Mono.zip(enterpriseService.serachCreditState(ev.getEnterprise()),
                 enterpriseService.searchSuperIntReports(ev.getEnterprise()),
                Mono.just(ev));
    }

    private Mono<EnterpriseValitation> searchRestrictions(EnterpriseValitation ev) {
        return enterpriseService.searchRestrictions(ev.getEnterprise())
                   .map(e -> {
                       boolean hasRes = e.getRestrictions().size() != 0;
                       return ev.toBuilder().enterprise(ev.getEnterprise()).hasRestriction(hasRes).build();
                   });
    }
}
