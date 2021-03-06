package pesoklp13.examples.tests.dummy.controller

import io.swagger.annotations.*
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import pesoklp13.examples.tests.dummy.model.Dummy
import pesoklp13.examples.tests.dummy.model.DummySourceSystem
import pesoklp13.examples.tests.dummy.service.DummyService
import javax.servlet.http.HttpServletResponse

@Api(tags = ["Dummy Controller"])
@RequestMapping(value = ["/api/dummies"], produces = ["application/json"])
@RestController
class DummyController(private val dummyService: DummyService) {

    @ApiOperation(
        value = "get list of dummies",
        response = Dummy::class,
        responseContainer = "List",
        tags = ["dummy"],
        nickname = "getDummies"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Successful",
            response = Dummy::class
        ), ApiResponse(code = 404, message = "Dummies not found")]
    )
    @RequestMapping(method = [RequestMethod.GET])
    fun getDummies(response: HttpServletResponse): List<Dummy>? {
        val dummies = dummyService.getDummies(null)
        if (dummies.isEmpty()) {
            response.status = HttpServletResponse.SC_NOT_FOUND
        }

        return dummies
    }

    @ApiOperation(
        value = "get list of dummies filtered by source system",
        response = Dummy::class,
        responseContainer = "List",
        nickname = "getDummiesBySourceSystem"
    )
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Successful",
            response = Dummy::class
        ), ApiResponse(code = 400, message = "Bad request"), ApiResponse(code = 404, message = "Dummies not found")]
    )
    @RequestMapping(path = ["/source-system/{sourceSystem}"], method = [RequestMethod.GET])
    fun getDummiesBySourceSystem(
        @ApiParam(value = "sourceSystem", required = true, allowableValues = "INTERNAL, EXTERNAL")
        @PathVariable("sourceSystem")
        sourceSystem: DummySourceSystem, response: HttpServletResponse
    ): List<Dummy>? {
        val dummies = dummyService.getDummies(sourceSystem)
        if (dummies.isEmpty()) {
            response.status = HttpServletResponse.SC_NOT_FOUND
        }

        return dummies
    }

    @ApiOperation(value = "get detail of dummy", response = Dummy::class, nickname = "getDummyDetail")
    @ApiResponses(
        value = [ApiResponse(
            code = 200,
            message = "Successful",
            response = Dummy::class
        ), ApiResponse(code = 400, message = "Bad request"), ApiResponse(code = 404, message = "Dummy not found")]
    )
    @RequestMapping(path = ["/{id}"], method = [RequestMethod.GET])
    fun getDummyDetail(
        @ApiParam(value = "id", required = true, example = "1")
        @PathVariable("id")
        id: Long, response: HttpServletResponse
    ): Dummy? {
        val dummy = dummyService.getDummyDetail(id)
        if (dummy == null) {
            response.status = HttpServletResponse.SC_NOT_FOUND
        }

        return dummy
    }
}