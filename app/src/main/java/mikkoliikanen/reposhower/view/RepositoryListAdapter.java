package mikkoliikanen.reposhower.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
import java.util.Map;

import mikkoliikanen.reposhower.R;
import mikkoliikanen.reposhower.model.Commit;
import mikkoliikanen.reposhower.model.Repository;

public class RepositoryListAdapter extends BaseAdapter {
    private final List<Repository> repositories;
    private Map<String, Commit> commits;

    public RepositoryListAdapter(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public int getCount() {
        return repositories.size();
    }

    @Override
    public Object getItem(int position) {
        return repositories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.repository_item, parent, false);

            RepositoryPresenter presenter = new RepositoryPresenter(convertView);
            convertView.setTag(presenter);
        }

        final Repository repository = (Repository) getItem(position);
        final Commit commit = commits != null ? commits.get(repository.getName()) : null;
        ((RepositoryPresenter) convertView.getTag()).present(repository, commit);
        return convertView;
    }

    public void setCommits(Map<String, Commit> commits) {
        this.commits = commits;
        notifyDataSetChanged();
    }
}
