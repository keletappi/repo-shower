package mikkoliikanen.reposhower.parser;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import mikkoliikanen.reposhower.model.Repository;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class RepositoriesResponseParserTest {


    private static final String TEST_JSON = "[" +
            "{\"foo\": \"bar\"}," +
            "{\"zip\": \"zap\"}," +
            "{\"fiz\": \"ban\"}" +
            "]";

    @Mock
    private RepositoryParser mockRepositoryParser;
    @Mock
    private Repository mockRepo1;
    @Mock
    private Repository mockRepo2;
    @Mock
    private Repository mockRepo3;

    @InjectMocks
    RepositoriesResponseParser testParser;

    @Before
    public void setUp() throws Exception {
        org.mockito.MockitoAnnotations.initMocks(this);

        when(mockRepositoryParser.parse(any(JSONObject.class)))
                .thenReturn(mockRepo1, mockRepo2, mockRepo3);
    }

    @Test
    public void testParse() throws Exception {
        List<Repository> result = testParser.parse(TEST_JSON);
        assertThat(result, contains(mockRepo1, mockRepo2, mockRepo3));
        verify(mockRepositoryParser, times(3)).parse(any(JSONObject.class));
        verifyNoMoreInteractions(mockRepositoryParser);
    }
}