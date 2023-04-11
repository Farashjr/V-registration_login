package com.example.v_registration_login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.v_registration_login.Models.ModelProducts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

class MainSellerActivity extends AppCompatActivity {

    private ImageButton logoutBtn, editProfileBtn, addProductBtn, filterProductBtn;
    private EditText searchProductET;
    private ShapeableImageView profileTv;
    private TextView nameTv, shopNameTv, emailTv, tabProductsTV, tabsOrdersTv,filteredProductsTv;
    private RelativeLayout productsRl, ordersRl;
    private RecyclerView productsRv;

    private FirebaseAuth db;
    private ProgressDialog progressDialog;
    private ArrayList<ModelProducts> productList;
    private AdapterProductSeller adapterProductSeller;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);

        logoutBtn = findViewById(R.id.logoutBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        addProductBtn = findViewById(R.id.addProductBtn);
        filterProductBtn= findViewById(R.id.filterProductBtn);
        profileTv = findViewById(R.id.profileTv);
        tabProductsTV = findViewById(R.id.tabProductsTV);
        tabsOrdersTv = findViewById(R.id.tabsOrdersTv);
        searchProductET= findViewById(R.id.searchProductET);
        filteredProductsTv = findViewById(R.id.filteredProductsTv);
        nameTv = findViewById(R.id.nameTv);
        shopNameTv = findViewById(R.id.shopNameTv);
        productsRl= findViewById(R.id.productsRl);
        ordersRl = findViewById(R.id.ordersRl);
        productsRv = findViewById(R.id.productsRv);


        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait ....");
        progressDialog.setCanceledOnTouchOutside(false);
        db = FirebaseAuth.getInstance();
        checkUser();
        loadAllProducts();

        showProductsUI();

        searchProductET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProductSeller.getFilter().filter(s);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout button click
                makeMeOffline();
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit profile button click
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add product button click
                startActivity(new Intent(MainSellerActivity.this, AddProductActivity.class));
            }
        });
        tabProductsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showProductsUI();
            }
        });
        tabsOrdersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOrdersUI();
            }
        });
        filterProductBtn. setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(MainSellerActivity.this);
                builder.setTitle("Choose Category:")
                        .setItems(Constants.productCategories, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selected = Constants.productCategories1(which);
                                filteredProductsTv.setText(selected);
                                if (selected.equals("All")){
                                    loadAllProducts();
                                }
                                else {
                                     loadFilteredProducts(selected);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private void loadFilteredProducts(String selected) {
        productList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(db.getUid()).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productList.clear();

                        for (DataSnapshot ds: snapshot.getChildren() ){
                            String productCategory = ""+ds.child("productCategory").getValue();

                            if (selected.equals(productCategory)){
                                ModelProducts modelProducts = ds.getValue(ModelProducts.class);
                                productList.add(modelProducts);
                            }

                        }
                        adapterProductSeller = new AdapterProductSeller(MainSellerActivity.this,productList);
                        productsRv.setAdapter(adapterProductSeller);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void loadAllProducts() {
        productList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(db.getUid()).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        productList.clear();
                        for (DataSnapshot ds: snapshot.getChildren() ){
                            ModelProducts modelProducts = ds.getValue(ModelProducts.class);
                            productList.add(modelProducts);
                        }
                        adapterProductSeller = new AdapterProductSeller(MainSellerActivity.this,productList);
                        productsRv.setAdapter(adapterProductSeller);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    @SuppressLint("ResourceType")
    private void showProductsUI(){
        productsRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);

        tabProductsTV.setTextColor(getResources().getColor(R.color.black));
        tabProductsTV.setBackgroundResource(R.drawable.shape_rect11);

        tabsOrdersTv.setTextColor(getResources().getColor(R.color.white));
        tabsOrdersTv.setBackgroundResource(getResources().getColor(android.R.color.transparent));
    }

    private void showOrdersUI(){
        productsRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);

        tabProductsTV.setTextColor(getResources().getColor(R.color.white));
        tabProductsTV.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabsOrdersTv.setTextColor(getResources().getColor(R.color.black));
        tabsOrdersTv.setBackgroundResource(R.drawable.shape_rect11);
    }

    private void makeMeOffline() {
        progressDialog.setMessage("Logging out...");
        HashMap<String, Object> hashMap = new HashMap<>();
         hashMap.put("online", "False");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(db.getUid()).updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        db.signOut();
                        checkUser();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MainSellerActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkUser() {
        FirebaseUser user = db.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(MainSellerActivity.this, Login.class));
            finish();
        } else {
            loadMyInfo();
        }
    }

    private void loadMyInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(db.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String name = "" + ds.child("name").getValue();
                            String accountType = "" + ds.child("accountType").getValue();
                            String email = "" + ds.child("email").getValue();
                            String shopName = "" + ds.child("shopName").getValue();

                            nameTv.setText(name);
                            shopNameTv.setText(shopName);
                            emailTv.setText(email);

                            try {
                                Picasso.get().load(String.valueOf(profileTv)).placeholder(R.drawable.ic_person_26).into(profileTv);
                            } catch (Exception e) {
                                profileTv.setImageResource(R.drawable.ic_person_26);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
    }
}
