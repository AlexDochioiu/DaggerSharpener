package com.github.alexdochioiu.example.screens;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.alexdochioiu.example.R;
import com.github.alexdochioiu.example.models.GithubRepo;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Alexandru Iustin Dochioiu on 21-Dec-18
 */
@SharpHomeActivityScope
class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.fullDate();

    private final Picasso picasso;
    private final Resources resources;

    private List<GithubRepo> repoList = new ArrayList<>(0);

    @Inject
    ReposAdapter(@NonNull Picasso picasso, @NonNull Resources resources) {
        this.picasso = picasso;
        this.resources = resources;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_repository, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(repoList.get(i));
    }

    @Override
    public int getItemCount() {
        return repoList.size();
    }

    void setRepoList(List<GithubRepo> repoList) {
        this.repoList = repoList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_name)
        TextView repoName;
        @BindView(R.id.user_avatar)
        ImageView userAvatar;
        @BindView(R.id.repo_description)
        TextView repoDescription;
        @BindView(R.id.repo_updated_at)
        TextView repoUpdatedAt;
        @BindView(R.id.repo_stars)
        TextView repoStars;
        @BindView(R.id.repo_forks)
        TextView repoForks;
        @BindView(R.id.repo_issues)
        TextView repoIssues;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(GithubRepo githubRepo) {
            final Locale locale = resources.getConfiguration().locale;

            repoName.setText(githubRepo.name);
            repoDescription.setVisibility(TextUtils.isEmpty(githubRepo.description) ? GONE : VISIBLE);
            repoDescription.setText(githubRepo.description);

            repoStars.setText(String.format(locale, "%d", githubRepo.stargazersCount));
            repoIssues.setText(String.format(locale, "%d", githubRepo.openIssuesCount));
            repoForks.setText(String.format(locale, "%d", githubRepo.forksCount));

            repoUpdatedAt.setText(resources
                    .getString(R.string.last_pushed, DATE_TIME_FORMATTER.print(githubRepo.updatedAt)));

            picasso.load(githubRepo.owner.avatarUrl)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(userAvatar);
        }
    }
}
