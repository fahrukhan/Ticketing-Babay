package com.fahru.ticketingbabay.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fahru.ticketingbabay.model.BaseModel;
import com.fahru.ticketingbabay.DB.DBHandler;
import com.fahru.ticketingbabay.R;
import com.fahru.ticketingbabay.object.ItemModel1;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.viewHolder> {
    BaseModel bm;
    protected String currentTable;
    private ArrayList<ItemModel1> list;
    private LocationAdapter adapter;

    public LocationAdapter(ArrayList<ItemModel1> list, String currentTable){
        this.list = list;
        this.adapter = this;
        this.currentTable = currentTable;
    }

    private ArrayList<ItemModel1> getData(){
        return list;
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView no, data;
        LinearLayout linLay;
        Context context;
        DBHandler db;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.dataItem1no);
            data = itemView.findViewById(R.id.dataItem1Name);
            linLay = itemView.findViewById(R.id.dataItemLinLay);
            context = itemView.getContext();
            db = new DBHandler(itemView.getContext());
            linLay.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select The Action");
            MenuItem edit = contextMenu.add(Menu.NONE, 1, getAdapterPosition(), "Edit");
            MenuItem delete = contextMenu.add(Menu.NONE, 2, getAdapterPosition(), "Delete");
            edit.setOnMenuItemClickListener(onEditMenu);
            delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu= new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                final int position  = menuItem.getOrder();
                final String id =  getData().get(position).getDataId();
                switch (menuItem.getItemId()) {
                    case 1:
                        final AlertDialog.Builder inputDialog = new AlertDialog.Builder(context);
                        View inputView = LayoutInflater.from(context).inflate(R.layout.input_data_model, null);
                        TextView label = inputView.findViewById(R.id.inputModel1label);
                        label.setText("EDIT");
                        final TextInputEditText inputName = inputView.findViewById(R.id.inputMode1Name);
                        inputName.setText(list.get(position).getName());

                        inputDialog.setPositiveButton("save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String name = inputName.getText().toString();
                                db.updateRecord(currentTable, id, name);
                                Toast.makeText(context, "DATA UPDATE", Toast.LENGTH_SHORT).show();
                                ItemModel1 model1 = new ItemModel1(list.get(position).getDataId(), name);
                                list.set(position, model1);
                                adapter.notifyDataSetChanged();
                                dialogInterface.cancel();
                            }
                        });
                        inputDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        inputDialog.setView(inputView);
                        inputDialog.show();
                        break;
                    case 2:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(list.get(position).getName()+"\n\nAKAN DIHAPUS, YAKIN..?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteRecord(currentTable, list.get(position).getDataId());
                                Toast.makeText(context, "BERHASIL DIHAPUS", Toast.LENGTH_SHORT).show();
                                list.remove(list.get(position));
                                adapter.notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        break;
                }

                return true;
            }
        };
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_model, parent, false);
        viewHolder vh = new viewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {

        final ItemModel1 model = list.get(position);
        String no = String.valueOf(position+1);
        holder.no.setText(no);
        holder.no.setTypeface(Typeface.create(setDigit(holder.context), Typeface.NORMAL));
        holder.data.setText(model.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected Typeface setDigit(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "digi.TTF");
    }
}