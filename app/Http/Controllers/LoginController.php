<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

class LoginController extends Controller
{
    public function login(Request $request)
    {
        $request->validate([
            'email' => 'required|string|max:255',
            'password' => 'required|min:8|string|max:255',
        ]);

        try {
            if ($request->get('email') == "admin@ardeco.com" && $request->get('password') == "admin@123") {
                session(['isLoggedIn' => true]);

                return redirect()->route('admin.dashboard');
            }

        } catch (\Throwable $e) {

        }
    }

    public function logout()
    {
        session()->forget('isLoggedIn');
        return redirect()->route('login');
    }
}
