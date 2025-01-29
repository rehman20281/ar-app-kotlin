<?php

namespace App\Http\Controllers;

use App\Http\Resources\ARModelResource;
use App\Models\ARModel;
use Illuminate\Http\Request;
use Intervention\Image\Drivers\Gd\Driver;
use Intervention\Image\ImageManager;

class ModelController extends Controller
{
    public function index(Request $request)
    {
        return view('add_model');
    }

    public function edit(Request $request, int $id)
    {
        $model = ARModel::find($id);
        if (! $model) {
            return redirect()->route('model.list');
        }

        return view('modify_model', compact('model'));
    }

    public function show(Request $request)
    {
        $models = ARModel::orderBy('id', 'desc')->paginate(10);
        return view('list_models', compact('models'));
    }

    public function delete(Request $request, int $id)
    {
        $model = ARModel::find($id);

        if ($model) {
            $thumbFullPath = storage_path("app/public/{$model->model_image}");

            if (file_exists($thumbFullPath)) {
                unlink($thumbFullPath);
            }

            $modelFile = storage_path("app/public/{$model->model_file}");

            if (file_exists($modelFile)) {
                unlink($modelFile);
            }

            $model->delete();
            session()->flash('msg', 'Model information deleted Successfully');
        }


        return redirect()->back();
    }

    public function save(Request $request)
    {
        // Validate the request input
        $request->validate([
            'title' => 'required|string|max:255',
            'model_image' => 'required|image|mimes:jpeg,png,jpg|max:2048',
            'model_file' => 'required|file|mimetypes:application/octet-stream,model/gltf-binary|max:10240',
            'description' => 'required|string|max:500',
        ]);

        $year = date('Y');
        $month = date('m');

        // Upload the model file
        $file = $request->file('model_file');
        $modelDir = "uploads/models/$year/$month/";
        $fileName = uniqid() . '.glb';
        $modelFilePath = $file->storeAs($modelDir, $fileName, 'public');

        // Process and upload the thumbnail image
        $manager = new ImageManager(new Driver());
        $photo = $request->file('model_image');
        $image = $manager->read($photo->getRealPath());
        $image->resize(413, 413);

        $imageFileName = time() . '.png';
        $thumbDir = "uploads/thumbs/$year/$month/".$imageFileName;

        $thumbFullPath = storage_path("app/public/{$thumbDir}");
        if (!is_dir(dirname($thumbFullPath))) {
            mkdir(dirname($thumbFullPath), 0755, true);
        }

        $image->save($thumbFullPath);

        ARModel::create([
            'title' => $request->get('title'),
            'model_image' => $thumbDir,
            'model_file' => $modelFilePath,
            'description' => $request->get('description'),
            'category_id' => $request->get('categoryId'),
        ]);

        session()->flash('msg', 'Model information saved Successfully');
        return redirect()->back();
    }

    public function update(Request $request, int $id)
    {
        // Validate the request input
        $request->validate([
            'title' => 'required|string|max:255',
            'model_image' => 'nullable|image|mimes:jpeg,png,jpg|max:2048',
            'model_file' => 'nullable|file|mimetypes:application/octet-stream,model/gltf-binary|max:10240',
            'description' => 'required|string|max:500',
        ]);

        // Retrieve the existing record
        $model = ARModel::findOrFail($id);

        $year = date('Y');
        $month = date('m');

        // Handle model file upload
        if ($request->hasFile('model_file')) {
            $file = $request->file('model_file');
            $modelDir = "uploads/models/$year/$month/";
            $fileName = uniqid() . '.glb';

            $modelFilePath = $file->storeAs($modelDir, $fileName, 'public');
            $model->model_file = $modelFilePath; // Update the file path in the database
        }

        // Handle image upload
        if ($request->hasFile('model_image')) {
            $manager = new ImageManager(new Driver());
            $photo = $request->file('model_image');
            $image = $manager->read($photo->getRealPath());
            $image->resize(413, 413);

            $imageFileName = time() . '.png';
            $thumbDir = "uploads/thumbs/$year/$month/$imageFileName";

            $thumbFullPath = storage_path("app/public/{$thumbDir}");
            if (!is_dir(dirname($thumbFullPath))) {
                mkdir(dirname($thumbFullPath), 0755, true);
            }

            $image->save($thumbFullPath);
            $model->model_image = $thumbDir;
        }

        // Update other fields
        $model->title = $request->get('title');
        $model->description = $request->get('description');
        $model->category_id = $request->get('categoryId');

        // Save the updated record
        $model->save();

        session()->flash('msg', 'Model information updated successfully');
        return redirect()->back();
    }



    public function getModels(Request $request, int $categoryId)
    {
        $models = ARModel::where('category_id', $categoryId)->orderBy('id', 'desc')->get();

        return ARModelResource::collection($models);
    }
}
