package mikkoliikanen.reposhower.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import mikkoliikanen.reposhower.R;
import mikkoliikanen.reposhower.model.Repository;

public class RepositoryListAdapter extends BaseAdapter {
    private final List<Repository> repositories;

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

        ((RepositoryPresenter) convertView.getTag()).present((Repository) getItem(position));
        return convertView;
    }
}
