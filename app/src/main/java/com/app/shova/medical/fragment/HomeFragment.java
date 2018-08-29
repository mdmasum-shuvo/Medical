package com.app.shova.medical.fragment;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.activity.ImportantMedicineActivity;
import com.app.shova.medical.adapter.WordListAdapter;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.data.firebase.LoadMedicineData;
import com.app.shova.medical.data.firebase.LoadUserData;
import com.app.shova.medical.data.sqlite.ImportantmDbController;
import com.app.shova.medical.data.sqlite.MedicineHistoryDbController;
import com.app.shova.medical.listener.FirebaseDataLoadListener;
import com.app.shova.medical.listener.OnItemClickListener;
import com.app.shova.medical.model.Medicine;
import com.app.shova.medical.model.User;
import com.app.shova.medical.utility.ActivityUtils;
import com.app.shova.medical.utility.DividerItemDecoration;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.turingtechnologies.materialscrollbar.AlphabetIndicator;
import com.turingtechnologies.materialscrollbar.DragScrollBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements TextToSpeech.OnInitListener {

    //variable
    private ArrayList<Medicine> medicineList;
    private ArrayList<Medicine> favWordList = null;
    private ArrayList<Medicine> historyList = null;
    private ArrayList<Medicine> filterWordList = null;
    private WordListAdapter wordListAdapter;
    //view
    private EditText etSearch;
    private TextToSpeech tts;
    private Activity mActivity;

    private ImageView imgVoiceIcon, imgSpeak;
    int position = 0;
    private RecyclerView recycleWordList;
    private LinearLayoutManager lytManagerWord;
    //database
    private FirebaseDatabase mDatabse;
    private ImportantmDbController dbControllerFav;
    private MedicineHistoryDbController dbControllerHis;
    private LoadMedicineData loadMedicineData;
    ImageView ivUnFavorite, ivFavorite;

    DatabaseReference mRef;
    private TextView tvVoice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_layout, container, false);
        initVariable();
        initView(v);
        initFuntionality();
        initListener();
        return v;
    }

    private void initView(View v) {
        ivUnFavorite = v.findViewById(R.id.un_favorite_medicine_icon);
        ivFavorite = v.findViewById(R.id.favorite_medicine_icon);
        etSearch = v.findViewById(R.id.searchBox);
        tvVoice = v.findViewById(R.id.voice_text);
        imgVoiceIcon = v.findViewById(R.id.voice_icon);
        imgSpeak = v.findViewById(R.id.speak_icon);
        recycleWordList = v.findViewById(R.id.recycleView_word_list);
        recycleWordList.setHasFixedSize(true);
        lytManagerWord = new LinearLayoutManager(mActivity);
        recycleWordList.setLayoutManager(lytManagerWord);
        recycleWordList.addItemDecoration(new DividerItemDecoration(mActivity, lytManagerWord.VERTICAL, 16));
        wordListAdapter = new WordListAdapter(mActivity, medicineList);
        recycleWordList.setAdapter(wordListAdapter);
        ((DragScrollBar) v.findViewById(R.id.dragScrollBar))
                .setIndicator(new AlphabetIndicator(mActivity), true);
    }


    private void initVariable() {
        mActivity = getActivity();
        mDatabse = FirebaseDatabase.getInstance();
        mRef = mDatabse.getReference(AppConstants.FIREBASE_MEDICINE_REF_KEY);
        dbControllerFav = new ImportantmDbController(mActivity);
        dbControllerHis = new MedicineHistoryDbController(mActivity);
        medicineList = new ArrayList<>();
        favWordList = new ArrayList<>();
        historyList = new ArrayList<>();
        filterWordList = new ArrayList<>();
        loadMedicineData = new LoadMedicineData(mActivity);


        tts = new TextToSpeech(mActivity, this);


    }

    private void initListener() {

        ivUnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.getInstance().invokeActivity(mActivity, ImportantMedicineActivity.class, false);
            }
        });


        wordListAdapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemListener(View view, int position) {
                loadFilterData();
                switch (view.getId()){
                    case R.id.imageView_pronounce:
                        speakOut(position);
                        break;
                    default:
                        checkHistoryData(position);
                        break;
                }
            }
        });

        imgSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etSearch.equals("")) {
                    speakOut(etSearch.getText().toString());
                }
            }
        });


        imgVoiceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //wordListAdapter.getFilter().filter(s);
                filterWordList = getFilterData(medicineList, s);
                wordListAdapter.setFilter(filterWordList);
                if (filterWordList.isEmpty()) {
                   // showEmptyView();
                } else {
                   // hideLoader();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    //filter wordlist data
    private ArrayList<Medicine> getFilterData(ArrayList<Medicine> models, CharSequence searchKey) {
        searchKey = searchKey.toString().toLowerCase();

        final ArrayList<Medicine> filteredModelList = new ArrayList<>();
        for (Medicine model : models) {
            final String word = model.getmName().toLowerCase();
            final String meaning = model.getmGenric().toLowerCase();

            if (word.contains(searchKey) || meaning.contains(searchKey)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private void loadFilterData() {
        if (!filterWordList.isEmpty()) {
            filterWordList.clear();
        }
        filterWordList.addAll(wordListAdapter.getDataList());
    }

    private void initFuntionality() {
        getFirebaseData();

    }


    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                //pronounce.setEnabled(true);
                //speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


    private void getFirebaseData() {

        loadMedicineData.getMedicineData();
        loadMedicineData.setClickListener(new FirebaseDataLoadListener() {
            @Override
            public void finishMedicineLoadData(ArrayList<Medicine> dataList, boolean isSuccessful) {
                if (isSuccessful) {
                    // Load data list
                    if (!medicineList.isEmpty()) {
                        medicineList.clear();
                    }
                    medicineList.addAll(dataList);
                    if (medicineList.size() > 0) {

                        // Sort word list by word
                        Collections.sort(medicineList, (Medicine s1, Medicine s2) -> {
                            return s1.getmName().compareToIgnoreCase(s2.getmName());
                        });
                        wordListAdapter.notifyDataSetChanged();
                    }

                    if (!AppConstants.MEDICINE_LIST.isEmpty()){
                        AppConstants.MEDICINE_LIST.clear();
                    }
                    AppConstants.MEDICINE_LIST.addAll(dataList);
                } else {
                    Toast.makeText(mActivity, "Data doesn't loaded", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, AppConstants.REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case AppConstants.REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etSearch.setText(result.get(0));
                }
                break;
            }
        }
    }


    //check history table is empty or similar word detail is exist
    private void checkHistoryData(int position) {
        String medicineName = medicineList.get(position).getmName(); //get the selected position data from wordList
        int count = 0;
        historyList = dbControllerHis.getAllMedicineData();
        if (!historyList.isEmpty()) {
            for (int i = 0; i < historyList.size(); i++) {
                if (medicineName.equalsIgnoreCase(historyList.get(i).getmName())) {
                    count++;
                }
            }
            if (count <= 0) {
                int insert = insertHistoryData(position);
                if (insert >= 0) {
                    Toast.makeText(mActivity, "save history", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mActivity, "History don't save", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            int insert = insertHistoryData(position);
            if (insert >= 0) {
                Toast.makeText(mActivity, "save history", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(mActivity, "History don't save", Toast.LENGTH_LONG).show();
            }
        }

        sendDataToDetail(position);
    }

    //insert data to favorite table sqlite
    private int insertFavouriteData(int position) {
        String mName = medicineList.get(position).getmName();
        String mType = medicineList.get(position).getmType();
        String mGenric = medicineList.get(position).getmGenric();
        String mDescription = medicineList.get(position).getmDescription();
        String mCompany = medicineList.get(position).getmCompany();
        String mPrice = medicineList.get(position).getmPrice();
        int insert = dbControllerFav.insertData(mName, mType, mGenric, mDescription, mCompany, mPrice);
        return insert;
    }

    //insert data to history table sqlite
    private int insertHistoryData(int position) {
        String mName = medicineList.get(position).getmName();
        String mType = medicineList.get(position).getmType();
        String mGenric = medicineList.get(position).getmGenric();
        String mDescription = medicineList.get(position).getmDescription();
        String mCompany = medicineList.get(position).getmCompany();
        String mPrice = medicineList.get(position).getmPrice();
        int insert = dbControllerHis.insertData(mName, mType, mGenric, mDescription, mCompany, mPrice);
        return insert;
    }

    //check favorite table is empty or similar word detail is exist
    private void checkFavoriteData(String word) {
        for (int j = 0; j < medicineList.size(); j++) {
            if (word.equals(medicineList.get(j).getmName())) {
                int count = 0;
                favWordList = dbControllerFav.getAllData(); //get all data from sqlite
                if (!favWordList.isEmpty()) {
                    for (int i = 0; i < favWordList.size(); i++) {

                        if (word.equalsIgnoreCase(favWordList.get(i).getmName())) {
                            //if data found insert don't work
                            count++;
                        }
                    }

                    if (count <= 0) {
                        // here data is inserted into database
                        int insert = insertFavouriteData(j);
                        if (insert > 0) {
                            Toast.makeText(mActivity, "Add to favorite", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mActivity, "favorite don't save", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(mActivity, "Allready in favorite list", Toast.LENGTH_LONG).show();
                    }

                } else {
                    int insert = insertFavouriteData(j);
                    if (insert >= 0) {
                        Toast.makeText(mActivity, "Add to favorite", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(mActivity, "favorite don't save", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }

        }
    }



    private void speakOut(String word) {
        tts.speak(word, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speakOut(int position) {
        tts.speak(filterWordList.get(position).getmName(), TextToSpeech.QUEUE_FLUSH, null);
    }

    private void sendDataToDetail(int position) {
        Medicine wordDetail = new Medicine(filterWordList.get(position).getmName(),
                filterWordList.get(position).getmType(), filterWordList.get(position).getmGenric(),
                filterWordList.get(position).getmDescription(), filterWordList.get(position).getmCompany(),
                filterWordList.get(position).getmPrice());
        ActivityUtils.invokeWordDetails(mActivity, wordDetail);

    }


}
