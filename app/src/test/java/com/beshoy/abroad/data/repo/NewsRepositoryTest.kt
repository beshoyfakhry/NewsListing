import com.beshoy.abroad.data.domain.NewsObject
import com.beshoy.abroad.data.domain.NewsResponse
import com.beshoy.abroad.data.repo.NewsApi
import com.beshoy.abroad.data.repo.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
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

        coEvery { mockApi.getEverything(any()) } returns NewsResponse("", 1, mockArticles)

        val result = repository.getEverything("bitcoin").articles

        assertEquals(1, result.size)
        assertEquals("Breaking News", result[0].author)
    }

    @Test
    fun fetchNewsException() = runTest {
        coEvery { mockApi.getEverything(any()) } throws Exception("Network error")

        try {
            repository.getEverything("bitcoin")
            fail("Expected an exception but got a result")
        } catch (e: Exception) {
            assertEquals("Network error", e.message)
        }
    }
}
