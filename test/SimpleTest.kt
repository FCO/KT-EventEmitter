import io.kotlintest.specs.StringSpec

class StringSpecExample : StringSpec() {
    init {
        "strings.length should return size of string" {
            "hello".length shouldBe 5
        }
    }
}