import io.restassured.RestAssured
import io.restassured.path.json.JsonPath
import spock.lang.Specification
import static io.restassured.RestAssured.expect
import static io.restassured.RestAssured.given

class LowestTempHour extends Specification {

    def setup() {
        given:
        RestAssured.baseURI = Payload.host()
    }

    def "TC 1: Valid zip code"() {
        given:
        def response = given().get(Payload.resource("27601"))
        JsonPath json = new JsonPath(response.asString())
        expect:
        response.statusCode() == 200
    }

    def "TC 2: Valid zip code with sub code"() {
        given:
        def response = given().get(Payload.resource("27601-1413"))
        JsonPath json = new JsonPath(response.asString())
        expect:
        response.statusCode() == 200
    }

    def "TC 3: Valid zip code and invalid sub code"() {
        given:
        def response = given().get(Payload.resource("27601-123"))
        JsonPath json = new JsonPath(response.asString())
        expect:
        response.statusCode() == 400
        json.get("error") == "You have entered invalid zip code."
    }

    def "TC 4: Invalid zip code"() {
        given:
        def response = given().get(Payload.resource("2761"))
        JsonPath json = new JsonPath(response.asString())
        expect:
        response.statusCode() == 400
        json.get("error") == "You have entered invalid zip code."
    }

    def "TC 5: Invalid zip code with alphabets"() {
        given:
        def response = given().get(Payload.resource("asd89"))
        JsonPath json = new JsonPath(response.asString())
        expect:
        response.statusCode() == 400
        json.get("error") == "You have entered invalid zip code."
    }

    def "TC 6: Zip code not available"() {
        given:
        def response = given().get(Payload.resource("00000"))
        JsonPath json = new JsonPath(response.asString())
        expect:
        response.statusCode() == 404
        json.get("error") == "No data found for given postal code."
    }
}
