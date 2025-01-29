<?php

namespace App\Http\Controllers;

use App\Models\User;
use App\Models\Design;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Http\Resources\DesignResource;
use App\Http\Resources\UserResource;


class ApiController extends Controller
{
    public function signup(Request $request)
    {
        $user = User::create([
            'name' => $request->get('name'),
            'email' => $request->get('email'),
            'mobile' => $request->get('mobile'),
            'password' => bcrypt($request->get('password')),
            'dob' => $request->get('dateOfBirth'),
        ]);

        return response()->json([
            'success' => true,
            'message' => 'Your account has been created successfully!'
        ]);
    }


    public function login(Request $request)
    {
        if (Auth::attempt(['email' => $request->email, 'password' => $request->password])) {
            $user = Auth::user();

            return response()->json([
                'success' => true,
                'message' => 'Login successful!',
                'user' => [
                    "name" => $user->name,
                    "id" => $user->id,
                    "email" => $user->email,
                    "dob" => $user->dob,
                    "mobile" => $user->mobile,
                ]
            ]);
        }

        return response()->json([
            'success' => false,
            'message' => 'Incorrect email or password',
        ], 401);
    }
    
    
    public function uploadDesign(Request $request)
    {
        $year = date('Y');
        $month = date('m');
        $modelFilePath = "";
        
        if ($request->hasFile('file')) {
            $file = $request->file('file');
            $modelDir = "/uploads/saved_design/$year/$month/";
            $fileName = uniqid().'.'.$file->extension();
            $modelFilePath = $file->storeAs($modelDir, $fileName, 'public');
        }

        $user = Design::create([
            'model_id' => $request->get('modelId'),
            'image' => $modelFilePath,
            'design' => $request->get('design'),
            'user_id' =>$request->get('userId'),
        ]);

        return response()->json([
            'success' => true,
            'message' => 'Design saved!'
        ]);
    }
    
    
    public function getSavedDesigns(Request $request, int $userId)
    {
        
        $models = Design::where('user_id', $userId)->orderBy('id', 'desc')->get();

        return DesignResource::collection($models);
    }
    
    
    public function getUser(Request $request, int $userId)
    {
        $user = User::find($userId);
    
        if (!$user) {
            return response()->json(['message' => 'User not found'], 404);
        }
    
        return new UserResource($user);
    }
    
    public function updateUser(Request $request, int $userId)
    {
        $user = User::find($userId);
    
        if (!$user) {
            return response()->json(['error' => 'User not found'], 404);
        }


        $user->name = $request->input('name');
        $user->email = $request->input('email');
        $user->mobile = $request->input('mobile');
        $user->dob = $request->input('dob');
        
        $user->save();

        return response()->json([
            'message' => 'User updated successfully',
            'user' => $user
        ]);
    }
}
