

#include <cv.h>
#include <highgui.h>
#include <classification.h>
#include  <iostream>
#include  <fstream>
#include  < string >
#include <pa_draw/pa_draw.h>
#pragma comment(lib, "classification_dll.lib")

using namespace cv;
using namespace std;

struct DetectObjectInfo{
	int image_id;
	int label;
	float score;
	int xmin;
	int ymin;
	int xmax;
	int ymax;

	Rect rect(){
		return Rect(xmin, ymin, xmax-xmin+1, ymax-ymin+1);
	}
};

vector<string> loadLabels(const char* labelsFile){
	ifstream fin(labelsFile);
	vector<string> out;

	string  s;
	while (fin >> s)
		out.push_back(s);

	return out;
}

vector<DetectObjectInfo> toDetInfo(FeatureResult* fr, int imWidth = 1, int imHeight = 1){
	vector<DetectObjectInfo> out;
	float* data = fr->list;
	for (int i = 0; i < fr->count; i += 7, data += 7){
		DetectObjectInfo obj;

		//if invalid det
		if (data[0] == -1)
			continue;
		
		obj.image_id = data[0];
		obj.label = data[1];
		obj.score = data[2];
		obj.xmin = data[3] * imWidth;
		obj.ymin = data[4] * imHeight;
		obj.xmax = data[5] * imWidth;
		obj.ymax = data[6] * imHeight;
		out.push_back(obj);
	}
	return out;
}

Scalar getColor(int label){
	static vector<Scalar> colors;
	if (colors.size() == 0){
#if 0
		for (float r = 127.5; r <= 256+127.5; r += 127.5){
			for (float g = 256; g >= 0; g -= 127.5){
				for (float b = 0; b <= 256; b += 127.5)
					colors.push_back(Scalar(b, g, r > 256 ? r - 256 : r));
			}
		}
#endif
		colors.push_back(Scalar(255, 0, 0));
		colors.push_back(Scalar(0, 255, 0));
		colors.push_back(Scalar(0, 0, 255));
		colors.push_back(Scalar(0, 255, 255));
		colors.push_back(Scalar(255, 0, 255));
		colors.push_back(Scalar(128, 0, 255));
		colors.push_back(Scalar(128, 255, 255));
		colors.push_back(Scalar(255, 128, 255));
		colors.push_back(Scalar(128, 255, 128));
	}
	return colors[label % colors.size()];
}

int main(int argc, char** argv){
	//disableErrorOutput();
	const char* caffemodel = "googlenet_iter_800.caffemodel";
	const char* prototxt = "deploy.prototxt";
	const char* labels = "data-labels.txt";
	const char* test_image = "test.jpg";
	
	vector<string> labelMap = loadLabels(labels);
	Mat im = imread(test_image);

	float means[] = { 104.0f, 117.0f, 123.0f };
	CaffeClassifier cc(prototxt, caffemodel, 1, 0, 3, means);
	FeatureResult* fr = cc.extfeature(im, "detection_out");	
	vector<DetectObjectInfo> objs = toDetInfo(fr, im.cols, im.rows);
	releaseFeatureResult(fr);

	for (int i = 0; i < objs.size(); ++i){
		auto obj = objs[i];
		if (obj.score > 0.25){
			rectangle(im, obj.rect(), getColor(obj.label), 2);
			paDrawString(&IplImage(im), labelMap[obj.label].c_str(), Point(obj.xmin, obj.ymin - 20), getColor(obj.label), 20);
			printf("%s: %f\n", labelMap[obj.label].c_str(), obj.score);
		}
	}
	imshow("demo", im);
	waitKey();
	return 0;
}