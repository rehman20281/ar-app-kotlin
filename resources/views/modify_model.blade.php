@extends('layouts.master')
@section('content')
    <!-- Begin Page Content -->
    <div class="container-fluid">

        <!-- Page Heading -->
        <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">Modify Model Information</h1>
        </div>

        <!-- Content Row -->
        <div class="row">
            <div class="col-xl-8 col-lg-7">
                <div class="card shadow mb-4">
                    <div class="card-body">
                        <form action="{{ route('model.update', ['id' => $model->id]) }}" method="post" enctype="multipart/form-data">
                            @csrf

                            <div class="mb-3">
                                <label for="title" class="form-label">Select Category</label>
                                <select class="form-control" name="categoryId">
                                    @foreach(['1'=>'Appliances', 2 => 'Furniture'] as $id=>$category)
                                        <option value="{{ $id }}" {{ ($model->id==$id) ?"selected":"" }}>{{ $category }}</option>
                                    @endforeach
                                </select>
                                @error('title')
                                    <div class="text-danger">{{ $message }}</div>
                                @enderror
                            </div>
                            <div class="mb-3">
                                <label for="title" class="form-label">Title</label>
                                <input type="text" value="{{ old('title', $model->title) }}" name="title" class="form-control" id="title" placeholder="Enter title" >
                                @error('title')
                                    <div class="text-danger">{{ $message }}</div>
                                @enderror
                            </div>

                            <!-- Model Image (File Input) -->
                            <div class="mb-3">
                                <label for="modelImage" class="form-label">Model Image</label>
                                <input type="file" name="model_image" accept=".jpg,.jpeg,.png" class="form-control" id="modelImage" ><br>
                                <img src="{{ asset('storage/app/public/'.$model->model_image) }}" width="100">
                                <input type="hidden" value="{{ $model->model_image }}" name="old_model_image">

                                @error('model_image')
                                <div class="text-danger">{{ $message }}</div>
                                @enderror
                            </div>

                            <!-- Model (File Input) -->
                            <div class="mb-3">
                                <label for="modelFile" class="form-label">Model File</label>
                                <input type="file" accept=".glb" name="model_file" class="form-control" id="modelFile" >

                                @if(!empty($model->model_file))
                                    <br>
                                    <a href="{{ url('public/storage/'.$model->model_file) }}"><img src="{{ asset('public/assets/img/glbicon.jpg') }}" width="60"></a>
                                @endif

                                <input type="hidden" value="{{ $model->model_file }}" name="old_model_file">

                                @error('model_file')
                                <div class="text-danger">{{ $message }}</div>
                                @enderror
                            </div>

                            <!-- Model Description -->
                            <div class="mb-3">
                                <label for="modelDescription" class="form-label">Model Description</label>
                                <textarea name="description" class="form-control" id="modelDescription" rows="3" placeholder="Enter model description" >{{ old('description', $model->description) }} </textarea>
                                @error('description')
                                    <div class="text-danger">{{ $message }}</div>
                                @enderror
                            </div>

                            <button type="submit" class="btn btn-primary btn-lg"><i class="fa fa-save"></i> Update</button>
                        </form>

                        @if(session()->has('msg'))
                            <p class="alert alert-success mt-3">{{ session('msg')}}</p>
                        @endif
                    </div>
                </div>
            </div>
        </div>
    </div>
@stop
