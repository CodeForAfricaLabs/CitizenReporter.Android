package org.codeforafrica.citizenreporterandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.codeforafrica.citizenreporterandroid.data.models.Story;
import org.codeforafrica.citizenreporterandroid.data.sources.LocalDataHelper;
import org.codeforafrica.citizenreporterandroid.main.MainActivity;
import org.codeforafrica.citizenreporterandroid.main.stories.StoriesFragment;
import org.codeforafrica.citizenreporterandroid.utils.Constants;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Storyboard extends AppCompatActivity {
    @BindView(R.id.slider) SliderLayout storiesSlider;
    @BindView(R.id.summary) TextView summary;
    @BindView(R.id.cause) TextView whatCausedthis;
    @BindView(R.id.who_is_involved) TextView whoIsInvolved;
    @BindView(R.id.when_happened) TextView whenDidItHappen;
    private Story story;
    private LocalDataHelper dataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyboard);
        dataHelper = new LocalDataHelper(this);
        ButterKnife.bind(this);
        String action = getIntent().getAction();

        if (action.equals(Constants.ACTION_EDIT_VIEW_STORY)) {
            int storyID = getIntent().getIntExtra("STORY_ID", -1);
            if (storyID > -1) {
                story = dataHelper.getStory(storyID);

                summary.setText(story.getTitle());
                whoIsInvolved.setText(story.getWho());
                whenDidItHappen.setText(story.getWhen());
                whatCausedthis.setText(story.getCause());


            } else {
                Toast.makeText(this, "This story can not be found",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }

        //Create HashMap for SliderLayout
        HashMap<String, String> imageUrlMaps = new HashMap<>();
        imageUrlMaps.put("Citizen Reporter", "http://media.cleveland.com/pdextra/photo/pittsburgh-police-g20jpg-6ac2c6b9b15b78ef.jpg");
        imageUrlMaps.put("Citizen Reporter 2", "https://assets.fastcompany.com/image/upload/w_1280,f_auto,q_auto,fl_lossy/fc/3034902-poster-p-1-3034902-poster-riotpolice.jpg");
        imageUrlMaps.put("Citizen Reporter 3", "http://media.gettyimages.com/photos/baltimore-firefighters-prepare-to-connect-a-hose-while-inspecting-a-picture-id471446974?s=612x612");

        //Loop through the hash map
        for(String name: imageUrlMaps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);

            textSliderView
                    .description(name)
                    .image(imageUrlMaps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            storiesSlider.addSlider(textSliderView);

        }
    }

    public void addImagesToSliderLayout(String[] images) {
        // TODO
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //    @OnClick(R.id.summary_view)
//    private static void openSummaryDialog() {}
//
//    @OnClick(R.id.when_view)
//    private static void openCalendar() {}
//
//    @OnClick(R.id.who_view)
//    private static void openWhoIsInvolvedDialog() {}
//
//    @OnClick(R.id.caused_view)
//    private static void openWhatCausedThis() {}
}
