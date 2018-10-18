package mikkoliikanen.reposhower.parser;

import org.json.JSONObject;
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
public class ArrayResponseParserTest {


    private static final String TEST_JSON = "[" +
            "{\"foo\": \"bar\"}," +
            "{\"zip\": \"zap\"}," +
            "{\"fiz\": \"ban\"}" +
            "]";

    @Mock
    private ResponseParser mockResponseParser;
    @Mock
    private Object object1;
    @Mock
    private Object object2;
    @Mock
    private Object object3;

    @InjectMocks
    ArrayResponseParser testParser;

    @Before
    public void setUp() throws Exception {
        org.mockito.MockitoAnnotations.initMocks(this);

        when(mockResponseParser.parse(any(JSONObject.class)))
                .thenReturn(object1, object2, object3);
    }

    @Test
    public void testParse() throws Exception {
        List<Repository> result = testParser.parse(TEST_JSON);
        assertThat(result, contains(object1, object2, object3));
        verify(mockResponseParser, times(3)).parse(any(JSONObject.class));
        verifyNoMoreInteractions(mockResponseParser);
    }
}