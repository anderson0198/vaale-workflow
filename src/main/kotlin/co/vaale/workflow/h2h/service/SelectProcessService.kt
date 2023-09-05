package co.vaale.workflow.h2h.service

import co.vaale.workflow.Application
import co.vaale.workflow.h2h.port.out.ISegmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.runApplication
import org.springframework.stereotype.Service

@Service
class SelectProcessService {

    @Autowired
    private lateinit var segmentPort: ISegmentRepository

    fun invoke(data: String) : String? {
        var segmentList = segmentPort.getActivityByProcessId("as")
        var returnProcess: Long? = null

        if (segmentList != null) {

            for (segment in segmentList) {
                var processName = segment["processName"] as String?

                println("mapa : $segment")

                if (processName == "dia uno") {
                    returnProcess = segment["processSegmentId"] as Long?
                    break
                }


            }

        }
        println(returnProcess)
        return returnProcess.toString()
    }

}

fun main(args: Array<String>) {
    val context = runApplication<Application>(*args)

    val selectProcessService = context.getBean(SelectProcessService::class.java)
    selectProcessService.invoke("asd")

    // Cerrar el contexto de Spring
    context.close()
}