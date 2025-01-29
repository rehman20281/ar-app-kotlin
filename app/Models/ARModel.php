<?php

namespace App\Models;

use Carbon\Carbon;
use Illuminate\Database\Eloquent\Model;

class ARModel extends Model
{
    protected $table = 'models';
    protected $fillable = [
        'title',
        'description',
        'model_image',
        'model_file',
        'category_id',
    ];

    public function getCreatedAtAttribute($data)
    {
        return Carbon::parse($data)->format('d-m-Y H:i:s');
    }
}
