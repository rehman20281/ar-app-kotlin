<?php

use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('login');
})->name('login')->middleware(\App\Http\Middleware\NotAuthMiddleware::class);

Route::get('/admin/dashboard', function () {
    return view('index');
})->name('admin.dashboard')->middleware(\App\Http\Middleware\AuthMiddleware::class);

Route::get('/model/add', [\App\Http\Controllers\ModelController::class, 'index'])->name('model.add')->middleware(\App\Http\Middleware\AuthMiddleware::class);
Route::get('/model/list', [\App\Http\Controllers\ModelController::class, 'show'])->name('model.list')->middleware(\App\Http\Middleware\AuthMiddleware::class);
Route::get('/model/edit/{id}', [\App\Http\Controllers\ModelController::class, 'edit'])->name('model.edit')->middleware(\App\Http\Middleware\AuthMiddleware::class);
Route::get('/model/delete/{id}', [\App\Http\Controllers\ModelController::class, 'delete'])->name('model.delete')->middleware(\App\Http\Middleware\AuthMiddleware::class);
Route::post('/model/save', [\App\Http\Controllers\ModelController::class, 'save'])->name('model.save')->middleware(\App\Http\Middleware\AuthMiddleware::class);
Route::post('/model/update/{id}', [\App\Http\Controllers\ModelController::class, 'update'])->name('model.update')->middleware(\App\Http\Middleware\AuthMiddleware::class);
Route::post('/admin/login', [\App\Http\Controllers\LoginController::class, 'login'])->name('admin.login');
Route::get('/admin/logout', [\App\Http\Controllers\LoginController::class, 'logout'])->name('admin.logout');


Route::get('/csrf-token', function () {
    return response()->json(['csrfToken' => csrf_token()]);
});

//API
Route::prefix('/api/v1')->group(function () {
    Route::get('/models/{categoryId}', [\App\Http\Controllers\ModelController::class, 'getModels']);
    Route::get('/signup', [\App\Http\Controllers\ApiController::class, 'signup']);
    Route::get('/login', [\App\Http\Controllers\ApiController::class, 'login']);
    Route::get('/saved-design/{userId}', [\App\Http\Controllers\ApiController::class, 'getSavedDesigns']);
    Route::post('/upload/design', [\App\Http\Controllers\ApiController::class, 'uploadDesign'])->name("upload.design");
    Route::get('/user/{userId}', [\App\Http\Controllers\ApiController::class, 'getUser']);
    Route::put('/user/update/{userId}', [\App\Http\Controllers\ApiController::class, 'updateUser']);
});
