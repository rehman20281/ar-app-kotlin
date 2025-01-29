<?php

namespace App\Models;

use Carbon\Carbon;
use Illuminate\Database\Eloquent\Model;

class Design extends Model
{
    protected $table = 'saved_design';
    
    protected $fillable = [
        'user_id', 
        'model_id', 
        'design',
        'image'
    ];

    public function getCreatedAtAttribute($data)
    {
        return Carbon::parse($data)->format('d-m-Y H:i:s');
    }
    
    
    public function model()
    {
        return $this->hasOne(ARModel::class, 'id', 'model_id');
    }
}
