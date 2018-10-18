package mikkoliikanen.reposhower;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RepoListingActivity extends Activity {

    private View repoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_listing);
        repoList = findViewById(R.id.repo_list);
    }
}
