package com.pk.contactslist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    private ArrayList<Contact> contactsList;
    private OnItemSelectedListener onItemSelectedListener;

    public ContactsAdapter(ArrayList<Contact> contactsList, OnItemSelectedListener onItemSelectedListener) {
        this.contactsList = contactsList;
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView nameTextView, phoneTextView;
        OnItemSelectedListener onItemSelectedListener;

        private ContactsViewHolder(LinearLayout layout, OnItemSelectedListener onItemSelectedListener) {
            super(layout);
            nameTextView = layout.findViewById(R.id.fullname_textView);
            phoneTextView = layout.findViewById(R.id.PhoneNumber_textView);
            this.onItemSelectedListener = onItemSelectedListener;

            layout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemSelectedListener.onItemSelected(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.contact_list_item_view, viewGroup, false
        );
        return new ContactsViewHolder(layout, onItemSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder ContactsViewHolder, int i) {
        final Contact contact = contactsList.get(i);
        ContactsViewHolder.nameTextView.setText(contact.getFullName());
        ContactsViewHolder.phoneTextView.setText(contact.getPhone());

    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }
    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

}
