package com.example.sudhanshus.multipleview.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.sudhanshus.multipleview.R;
import com.example.sudhanshus.multipleview.adapter.AdapterGridShopProductCard;
import com.example.sudhanshus.multipleview.adapter.EnquiryAdapter;
import com.example.sudhanshus.multipleview.model.Image;
import com.example.sudhanshus.multipleview.model.ShopProduct;
import com.example.sudhanshus.multipleview.utils.SpacingItemDecoration;
import com.example.sudhanshus.multipleview.utils.UserDetailsPref;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab_add;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private RecyclerView rlProducts;
    UserDetailsPref userDetailsPref;
    EnquiryAdapter enquiryAdapter;
    private ViewPager viewPager;
    private LinearLayout layout_dots;
    private AdapterImageSlider adapterImageSlider;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    AdapterGridShopProductCard mAdapter;
    List<ShopProduct> items = new ArrayList<>();


    private static int[] array_image_place = {
            R.drawable.image_12,
            R.drawable.image_13,
            R.drawable.image_14,
            R.drawable.image_15,
            R.drawable.image_8,
    };
    private static final String[] ENQUIRY_ELEMENT = new String[]{
            "Enquiry", "Contact Us"
    };
    private boolean[] clicked_enquiry = new boolean[ENQUIRY_ELEMENT.length];

    private static String[] array_title_place = {
            "Dui fringilla ornare finibus, orci odio",
            "Mauris sagittis non elit quis fermentum",
            "Mauris ultricies augue sit amet est sollicitudin",
            "Suspendisse ornare est ac auctor pulvinar",
            "Vivamus laoreet aliquam ipsum eget pretium",
    };

    private static String[] array_brief_place = {
            "Foggy Hill",
            "The Backpacker",
            "River Forest",
            "Mist Mountain",
            "Side Park",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        loginCheck();
        initToolbar();
        initComponent();
        initNavigationMenu();
        initListener();
    }

    private void initData() {
        userDetailsPref = UserDetailsPref.getInstance();
        rlProducts = (RecyclerView) findViewById(R.id.rlProducts);
        rlProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rlProducts.addItemDecoration(new SpacingItemDecoration(2, 8, true));
        rlProducts.setHasFixedSize(true);
        rlProducts.setNestedScrollingEnabled(false);


        //set data and list adapter
        mAdapter = new AdapterGridShopProductCard(MainActivity.this, items);
        rlProducts.setAdapter(mAdapter);

        // on item list clicked
        mAdapter.setOnItemClickListener(new AdapterGridShopProductCard.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ShopProduct obj, int position) {
               // Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnMoreButtonClickListener(new AdapterGridShopProductCard.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, ShopProduct obj, MenuItem item) {
                //Snackbar.make(parent_view, obj.title + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
            }
        });


    }

    private void loginCheck() {
        if(userDetailsPref.getIntPref(MainActivity.this, UserDetailsPref.USER_ID) != 0){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initListener() {
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EnquiryActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initView() {
        fab_add = findViewById(R.id.fab_add);
        layout_dots = (LinearLayout) findViewById(R.id.layout_dots);
        viewPager = (ViewPager) findViewById(R.id.pager);
        rlProducts = findViewById(R.id.rlProducts);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("Drawer News");
        //Tools.setSystemBarColor(this);
    }

    private void initComponent() {
        adapterImageSlider = new AdapterImageSlider(this, new ArrayList<Image>());

        final List<Image> items = new ArrayList<>();
        for (int i = 0; i < array_image_place.length; i++) {
            Image obj = new Image();
            obj.image = array_image_place[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.name = array_title_place[i];
            obj.brief = array_brief_place[i];
            items.add(obj);
        }

        adapterImageSlider.setItems(items);
        viewPager.setAdapter(adapterImageSlider);

        // displaying selected image first
        viewPager.setCurrentItem(0);
        addBottomDots(layout_dots, adapterImageSlider.getCount(), 0);
        ((TextView) findViewById(R.id.title)).setText(items.get(0).name);
        ((TextView) findViewById(R.id.brief)).setText(items.get(0).brief);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int pos, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int pos) {
                ((TextView) findViewById(R.id.title)).setText(items.get(pos).name);
                ((TextView) findViewById(R.id.brief)).setText(items.get(pos).brief);
                addBottomDots(layout_dots, adapterImageSlider.getCount(), pos);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startAutoSlider(adapterImageSlider.getCount());
    }

    private void addBottomDots(LinearLayout layout_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layout_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 0, 10, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            dots[i].setColorFilter(ContextCompat.getColor(this, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
            layout_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
            dots[current].setColorFilter(ContextCompat.getColor(this, R.color.grey_40), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }


    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<Image> items;

        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, Image obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        // constructor
        private AdapterImageSlider(Activity activity, List<Image> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public Image getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<Image> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final Image o = items.get(position);
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = (ImageView) v.findViewById(R.id.image);
            MaterialRippleLayout lyt_parent = (MaterialRippleLayout) v.findViewById(R.id.lyt_parent);
            /*Tools.displayImageOriginal(act, image, o.image);
            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });*/
            ((ViewPager) container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }

    @Override
    public void onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    private void initNavigationMenu(){
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                actionBar.setTitle(item.getTitle());
                Log.e("Drawer Element", ""+item.getTitle() + " - ");
                switch (item.getTitle().toString()){
                    case "Contact Us":
                        //showSingleChoiceDialog();
                        Intent intentEnquiry = new Intent(MainActivity.this, ContactListActivity.class);
                        startActivity(intentEnquiry);
                        break;

                    case "Enquiry":
                        //showSingleChoiceDialog();
                        Intent intentContact = new Intent(MainActivity.this, EnquiryListActivity.class);
                        startActivity(intentContact);
                        break;

                    case "Logout":
                        logOut();
                        break;
                }
                drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start
        //drawer.openDrawer(GravityCompat.START);
    }

    private void logOut(){
        userDetailsPref.putIntPref(MainActivity.this, UserDetailsPref.USER_ID, 0);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private String single_choice_selected;

    private void showSingleChoiceDialog() {
        single_choice_selected = ENQUIRY_ELEMENT[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Type of Enquiry");
        builder.setSingleChoiceItems(ENQUIRY_ELEMENT, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                single_choice_selected = ENQUIRY_ELEMENT[i];
                switch(i){
                    case 0:
                        Intent contact = new Intent(MainActivity.this, EnquiryActivity.class);
                        startActivity(contact);
                        break;
                    case 1:
                        Intent enquiry = new Intent(MainActivity.this, ContactActivity.class);
                        startActivity(enquiry);
                        break;
                }
            }
        });
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Snackbar.make(parent_view, "selected : " + single_choice_selected, Snackbar.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.CANCEL, null);
        builder.show();
    }

}
