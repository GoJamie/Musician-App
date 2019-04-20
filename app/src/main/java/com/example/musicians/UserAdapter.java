package com.example.musicians;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        CardView user_card;
        TextView UserName;


        UserViewHolder(View userView) {
            super(userView);
            user_card = userView.findViewById(R.id.user_card);
            UserName = userView.findViewById(R.id.user_name);

        }
    }

    UserAdapter(List<User> Users){
        this.Users = Users;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View user_view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_template, viewGroup, false);
        UserViewHolder pvh = new UserViewHolder(user_view);
        return pvh;
    }

    @Override
    public void onBindViewHolder(UserViewHolder UserViewHolder, int i) {
        // TODO: Add correct user info
        UserViewHolder.UserName.setText(Users.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return Users.size();
    }
}
