package co.vaale.workflow.h2h.service

import co.vaale.workflow.h2h.port.out.ILoanPaymentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WorkflowCollectService {
    @Autowired
    private lateinit var loanPaymentPort : ILoanPaymentRepository


    operator fun invoke(){
        val list = loanPaymentPort.getClientWithActivePurchases()
        println(list)
    }
}