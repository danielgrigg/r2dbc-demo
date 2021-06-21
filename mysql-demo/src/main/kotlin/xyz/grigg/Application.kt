package xyz.grigg

import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.runtime.Micronaut.build
import io.micronaut.runtime.event.ApplicationStartupEvent
import reactor.core.publisher.Flux
import javax.inject.Singleton

fun main(args: Array<String>) {
	build()
		.args(*args)
		.packages("xyz.grigg")
		.start()
}

@Singleton
class SampleEventListener(private val repository: ThingRepository) : ApplicationEventListener<ApplicationStartupEvent> {

	override fun onApplicationEvent(event: ApplicationStartupEvent) {
		Flux.from(repository.saveAll((1..5).map { Thing(null, "thing-${it}") })).collectList().block()
	}
}

@MappedEntity
data class Thing(@GeneratedValue @field:Id var id: Long?, val name: String)


@Controller("/things")
class ThingController(private val repository: ThingRepository) {

	@Get
	fun all(): Flux<Thing> {
		return Flux.from(repository.findAll())
	}
}

@R2dbcRepository(dialect = Dialect.MYSQL)
interface ThingRepository : ReactiveStreamsCrudRepository<Thing, Long>
