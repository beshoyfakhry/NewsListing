import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.data.domain.NewsResponse
import com.beshoy.abroad.data.repo.NewsApi
import com.beshoy.abroad.data.repo.NewsRepository
import com.beshoy.abroad.data.repo.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class NewsRepositoryTest {

    private lateinit var mockApi: NewsApi
    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        mockApi = mockk<NewsApi>()
        repository = NewsRepository(mockApi)
    }

    @Test
    fun fetchNews() = runTest {
        val mockArticles = listOf(NewsObject("Breaking News", "Some description", "", "", "", ""))
        val newsResponse = NewsResponse("200", 10, mockArticles)


        coEvery { mockApi.getEverything(any()) } returns newsResponse

        val result = repository.getEverything("bitcoin")

        result.let {
            if (it is Resource.Success) {

                assertEquals(1, it.data.articles.size)
                assertEquals("Breaking News", it.data.articles[0].author)
            }
        }

    }


    @Test
    fun fetchNewsException() = runTest {

        val exceptionMessage = "Network error"

        coEvery { mockApi.getEverything(any(), any()) } throws Exception(exceptionMessage)


        val result = repository.getEverything("bitcoin")
        result.let {
            if (it is Resource.Error) {
                assertEquals(exceptionMessage, it.message)
            }
        }
    }
}
