@extends('layouts.master')
@section('content')
    <!-- Begin Page Content -->
    <div class="container-fluid">

        <!-- Page Heading -->
        <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">View Models</h1>
        </div>

        <!-- Content Row -->
        <div class="row">
            <div class="col-xl-12 col-lg-12">
                <div class="card shadow mb-4">
                    <div class="card-body">
                        @if(session()->has('msg'))
                            <p class="alert alert-success mt-3">{{ session('msg')}} ajhbjh </p>
                        @endif
                        <table class="table table-bordered table-striped">
                            <thead class="table-dark">
                                <th>Id</th>
                                <th>Title</th>
                                <th>Image</th>
                                <th>Description</th>
                                <th>Uploaded At</th>
                                <th>Action</th>
                            </thead>
                            <tbody>
                                @if(count($models) == 0)
                                    <tr><td colspan="6" class="table-light">No rows found!</td></tr>
                                @endif
                                @foreach($models as $model)
                                    <tr>
                                        <td>{{ $model->id }}</td>
                                        <td>{{ $model->title }}</td>
                                        <td><img src="{{ asset('storage/app/public/'.$model->model_image) }}" width="50"></td>
                                        <td>{{ $model->description }}</td>
                                        <td>{{ $model->created_at }}</td>
                                        <td class="d-flex justify-content-center">
                                            <a class="btn btn-light" href="{{ route('model.edit', ['id' =>$model->id]) }}"><i class="fas fa-fw fa-edit"></i></a>
                                            <a class="btn btn-danger" href="{{ route('model.delete', ['id' =>$model->id]) }}"><i class="fas fa-times"></i></a>
                                        </td>
                                    </tr>
                                @endforeach
                            </tbody>
                        </table>

                        <div class="d-flex justify-content-center">
                            {{ $models->render() }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
@stop
