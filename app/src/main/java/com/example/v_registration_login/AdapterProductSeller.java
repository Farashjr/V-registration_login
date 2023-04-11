package com.example.v_registration_login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.v_registration_login.Models.ModelProducts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductSeller extends RecyclerView.Adapter<AdapterProductSeller.HolderProductSeller> implements Filterable {

    public ArrayList<ModelProducts> productList;
    private Context context;
    public ArrayList<ModelProducts> ProductList, filterList;
    private FilterProduct filter;

    public AdapterProductSeller(Context context, ArrayList<ModelProducts> productList){
        this.context = context;
        this.ProductList = productList;
        this.filterList =  productList;
    }

    @NonNull
    @Override
    public HolderProductSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_seller,parent,false);
        return new HolderProductSeller(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductSeller holder, int position) {
        ModelProducts modelProducts =ProductList.get(position);
        String id = modelProducts.getProductId();
        String uid = modelProducts.getUid();
        String productCategory = modelProducts.getProductCategory();
        String productDescription = modelProducts.getProductDescription();
        String icon = modelProducts.getProductIcon();
        String quantity = modelProducts.getProductQuantity();
        String title = modelProducts.getProductTitle();
        String timestamp = modelProducts.getTimestamp();
        String price = modelProducts.getProductPrice();


        holder.titleTv.setText(title);
        holder.quantityTv.setText(title);
        holder.priceTv.setText(title);
        holder.titleTv.setText(title);

        try {
            Picasso.get().load(icon).placeholder(R.drawable.ic_shopping_cart_24).into(holder.productIconTV);
        }
        catch (Exception e){
            holder.productIconTV.setImageResource(R.drawable.ic_shopping_cart_24);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //handles items clicks, show item details
            }
        });

    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null){
            filter = new FilterProduct(this, filterList);
        }
        return filter;
    }

    class HolderProductSeller extends RecyclerView.ViewHolder {
        private ImageView productIconTV;
        private TextView titleTv, quantityTv, priceTv;
        public HolderProductSeller(@NonNull View itemView) {
            super(itemView);

            productIconTV = itemView.findViewById(R.id.productIconTV);
            titleTv = itemView.findViewById(R.id.titleTv);
            quantityTv = itemView.findViewById(R.id.quantityTv);
            priceTv = itemView.findViewById(R.id.priceTv);

        }
    }
}
