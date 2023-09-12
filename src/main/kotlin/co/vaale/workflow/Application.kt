package co.vaale.workflow

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.ExitCodeGenerator
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class Application : SpringBootServletInitializer(), CommandLineRunner {

	override fun run(vararg args: String?) {
		if (args.isNotEmpty() && args[0] == "exitcode") {
			throw ExitException()
		}
	}

	internal class ExitException : RuntimeException(), ExitCodeGenerator {
		override fun getExitCode(): Int {
			return 10
		}

		companion object {
			private const val serialVersionUID = 1L
		}
	}



}

fun main(args: Array<String>) {
	runApplication<Application>(*args)


}
