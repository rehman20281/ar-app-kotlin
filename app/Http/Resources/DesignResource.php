<?php

namespace App\Http\Resources;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class DesignResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'id' => $this->id,
            'model' => url('public/storage/'.$this->model->model_file),
            'image' => url('public/storage/'.$this->image),
            'created_at' => $this->created_at,
        ];
    }
}
