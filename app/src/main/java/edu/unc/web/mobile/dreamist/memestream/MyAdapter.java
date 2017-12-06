package edu.unc.web.mobile.dreamist.memestream;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by dreamist on 12/5/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
	FirebaseDatabase postdb;

	public static class ViewHolder extends RecyclerView.ViewHolder{
		public TextView mTextView;
		public ViewHolder (TextView v){
			super(v);
			mTextView = v;
		}
	}

	public MyAdapter(FirebaseDatabase postdbinstance){
		postdb = postdbinstance;
	}

	@Override
	public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		TextView v = (TextView)LayoutInflater.from(parent.getContext())
				.inflate(R.layout.my_text_view, parent, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}


	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		DatabaseReference dbref = postdb.getReference("posts");
		holder.mTextView.setText("" + position + "out of " + 4 + " Memes!");
	}

	@Override
	public int getItemCount() {
		return 4;
	}
}
